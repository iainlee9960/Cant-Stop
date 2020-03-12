import java.io.File;
import java.util.ArrayList;
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

public class SaveGame {
	public static final String xmlFilePath = "Save\\SaveGame1.xml"; // add number later

	public void saveGame(CantStopGame toSave) {
		int NumPlayer = toSave.getNumPlayer();
		int CurrentPlayer = toSave.getCurrentPlayerIndex();
		CantStopPlayer[] ColumnsCompleted = new CantStopPlayer[10];
		int[] CurrentPlayerNuetralSpaces;
		CantStopPlayer[] players = new CantStopPlayer[NumPlayer];
		Element[] playerElements = new Element[NumPlayer];
		for (int i = 0; i < NumPlayer; i++) {
			players[i] = toSave.getPlayer(i); // possible fail point
		}
		CurrentPlayerNuetralSpaces = players.get(CurrentPlayer).getRecord().getNeutralMarkerLocations();
		for (int i = 0; i < 10; i++) {
			ColumnsCompleted[i] = toSave.whoCompletedColumn(i); // this might give errors
		}

		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement("CantStopGameSave"); // add number later
			document.appendChild(root);

			// employee element

			Element playerOrder = document.createElement("playerOrder");

			root.appendChild(playerOrder);

			for (int i = 0; i < NumPlayer; i++) {
				playerElements[i]= document.createElement("Player"+i);
				playerOrder.appendChild(playerElements[i]);
				// set an attribute to staff element
				//need player color name and completed from Cant stop player
				//nned peice locations Num nuetral left and Nuetral peice location from records
				playerElements[i].appendChild(document.createTextNode(data));
			}
			// you can also use staff.setAttribute("id", "1") for this

			// firstname element
			Element firstName = document.createElement("firstname");
			firstName.appendChild(document.createTextNode("James"));
			employee.appendChild(firstName);

			// lastname element
			Element lastname = document.createElement("lastname");
			lastname.appendChild(document.createTextNode("Harley"));
			employee.appendChild(lastname);

			// email element
			Element email = document.createElement("email");
			email.appendChild(document.createTextNode("james@example.org"));
			employee.appendChild(email);

			// department elements
			Element department = document.createElement("department");
			department.appendChild(document.createTextNode("Human Resources"));
			employee.appendChild(department);

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

			System.out.println("Done creating XML File");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

}



