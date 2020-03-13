import java.awt.Color;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SaveGame {
	public String xmlFilePath; // add number later

	public void saveGame(CantStopGame toSave, int numSave) {
		xmlFilePath = "Save\\SaveGame" + numSave + ".xml";
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDateAndTime = myDateObj.format(myFormatObj);
		int NumPlayer = toSave.getNumPlayers();
		int CurrentPlayer = toSave.getCurrentPlayerIndex();
		int[] ColumnsCompleted = new int[12];
		int[] CurrentPlayerNuetralSpaces;
		CantStopPlayer[] players = new CantStopPlayer[NumPlayer];
		for (int i = 0; i < NumPlayer; i++) {
			players[i] = toSave.getPlayer(i); // possible fail point
		}
		for (int i = 0; i < 12; i++) {
			if (toSave.whoCompletedColumn(i) == null) {
				ColumnsCompleted[i] = -1;
			} else {
				int playerIndex = 0;
				boolean keepGoing = true;
				for (int j = 0; j < NumPlayer && keepGoing; j++) {
					if (toSave.whoCompletedColumn(i).equals(players[j])) {
						playerIndex = j;
						keepGoing = false;
					}
				}
				ColumnsCompleted[i] = playerIndex;
			}
		}
		CurrentPlayerNuetralSpaces = toSave.getPlayer(CurrentPlayer).getRecord().getNeutralMarkerLocations();

		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement("CantStopGameSave"); // add number later
			document.appendChild(root);
			// employee element
			Element dateAndTime = document.createElement("Date And Time");
			dateAndTime.appendChild(document.createTextNode(formattedDateAndTime));
			root.appendChild(dateAndTime);

			Element playerOrder = document.createElement("playerOrder");

			for (int i = 0; i < NumPlayer; i++) {
				Element playerNum = document.createElement("Player " + i);
				Attr type = document.createAttribute("type");
				if (players[i].isHuman()) {
					type.setValue("HUMAN");
					playerNum.setAttributeNode(type);
				}
				else {
					type.setValue("COMPUTER");
					Attr AiName = document.createAttribute("AI");
					AiName.setValue(players[i].getAIName());
					playerNum.setAttributeNode(type);
					playerNum.setAttributeNode(AiName);					
				}
				
				Element color = document.createElement("Color");
				Element name = document.createElement("Name");
				Element completed = document.createElement("Completed");
				Element pieceLocations = document.createElement("Piece Locations");

				// need player color name and completed from Cant stop player
				// nned peice locations and Nuetral peice location from records (Im assuming
				// that the int array for nuetral peice location can be used to find nuetral
				// left
				color.appendChild(document.createTextNode(String.valueOf(players[i].getPlayerColor())));
				name.appendChild(document.createTextNode(players[i].getPlayerName()));
				completed.appendChild(document.createTextNode(String.valueOf(players[i].getCompleted())));
				pieceLocations.appendChild(
						document.createTextNode(Arrays.toString(players[i].getRecord().getPieceLocations())));
				playerNum.appendChild(name);
				playerNum.appendChild(color);
				playerNum.appendChild(completed);
				playerNum.appendChild(pieceLocations);
				if (i == CurrentPlayer) {
					Element nuetralMarkerLocations = document.createElement("Nuetral Markers");
					nuetralMarkerLocations.appendChild(document
							.createTextNode(Arrays.toString(players[i].getRecord().getNeutralMarkerLocations())));
				}
				playerOrder.appendChild(playerNum);
			}
			root.appendChild(playerOrder);
			Element completedColumns = document.createElement("Completed Columns");
			completedColumns.appendChild(document.createTextNode(Arrays.toString(ColumnsCompleted)));
			root.appendChild(completedColumns);
			// create the xml file
			// transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(xmlFilePath));

			// If you use
			// StreamResult result = new StreamResult(System.out);
			// the output will be pushed to the standard output ...
			// You can use that for debugging

			transformer.transform(domSource, streamResult);

			// System.out.println("Done creating XML File");

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public CantStopGame returnGame(int numSave) {
		xmlFilePath = "Save\\SaveGame" + numSave + ".xml";
		String dateAndTime = "";
		int numPlayers = 0;
		int[] indexesOfColumnsCompleted;
		CantStopPlayer[] completedColumns = new CantStopPlayer[12];
		int currentPlayerIndex = 0;
		CantStopPlayer[] players;
		try {

			File file = new File(xmlFilePath);

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = dBuilder.parse(file);
			NodeList Nodes = doc.getChildNodes().item(0).getChildNodes();
			NodeList playerInfo = Nodes.item(1).getChildNodes();
			// Nodes.item(0).getNodeName();
			dateAndTime = Nodes.item(0).getTextContent();
			numPlayers = playerInfo.getLength();
			players = new CantStopPlayer[numPlayers];

			for (int i = 0; i < numPlayers; i++) {
				NodeList playerSpecs = playerInfo.item(i).getChildNodes();
				NamedNodeMap nodeMap = playerInfo.item(i).getAttributes();
				if (nodeMap.item(0).getNodeValue().contentEquals("HUMAN")) {
					players[i] = new HumanPlayer();
				} else {
					players[i] = new ComputerPlayer();
					((ComputerPlayer)players[i]).setAI(nodeMap.item(1).getNodeValue());
				}
				players[i].setPlayerName(playerSpecs.item(0).getTextContent());
				String[] subStr = { "r=", "g=", "b=" };
				int[] rgb = new int[3];
				for (int startIndex = 0, endIndex = 0, j = 0; j < 3; j++) {

					startIndex = playerSpecs.item(1).getTextContent().indexOf(subStr[j]) + 2;
					if (j < 2) {
						endIndex = playerSpecs.item(1).getTextContent().indexOf(",", startIndex);
					} else {
						endIndex = playerSpecs.item(1).getTextContent().indexOf("]");
					}
					rgb[j] = Integer.parseInt(playerSpecs.item(1).getTextContent().substring(startIndex, endIndex));
				}
				players[i].setPlayerColor(new Color(rgb[0], rgb[1], rgb[2]));
				for (int j = Integer.parseInt(playerSpecs.item(2).getTextContent()); j > 0; j--) {
					players[i].addCompleted();
				}
				players[i].getRecord().setPieceLocations(makeArray(playerSpecs.item(3).getTextContent()));
				if (playerSpecs.getLength() == 5) {
					currentPlayerIndex = i;
					players[i].getRecord().setNuetralLocations(makeArray(playerSpecs.item(4).getTextContent()));
				}
			}
			indexesOfColumnsCompleted = makeArray(Nodes.item(2).getTextContent());
			for (int i = 0; i < 12; i++) {
				if (indexesOfColumnsCompleted[i] == -1) {
					completedColumns[i] = null;
				} else {
					completedColumns[i] = players[indexesOfColumnsCompleted[i]];
				}
			}
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new CantStopGame(players, currentPlayerIndex, completedColumns);
	}

	private int[] makeArray(String stringVal) {
		if (stringVal.length() > 2) {
			ArrayList<Integer> commaIndexes = new ArrayList<Integer>();
			int intSize;
			int[] returnInt;
			boolean nextValExists = true;
			for (int j = 0; nextValExists;) {
				j = stringVal.indexOf(",", j);
				if (j == -1) {
					nextValExists = false;
				} else {
					commaIndexes.add(j);
					j++;
				}
			}
			intSize = commaIndexes.size() + 1;
			returnInt = new int[intSize];
			for (int i = 0; i < intSize; i++) {
				if (i == 0) {
					if (commaIndexes.size() > i) {
						returnInt[i] = Integer.parseInt(stringVal.substring(1, commaIndexes.get(i)));
					} else {
						returnInt[i] = Integer.parseInt(stringVal.substring(1, stringVal.length() - 1));
					}
				} else if (i == commaIndexes.size()) {
					returnInt[i] = Integer.parseInt(
							stringVal.substring(commaIndexes.get(commaIndexes.size() - 1) + 2, stringVal.length() - 1));
				} else {
					returnInt[i] = Integer
							.parseInt(stringVal.substring(commaIndexes.get(i - 1) + 2, commaIndexes.get(i)));
				}

			}
			return returnInt;
		} else
			return new int[0];
	}

}






