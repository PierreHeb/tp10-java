import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import javax.json.*;

public class Client2
{

	public static void main(String[] args)
	{
		try{

			if( args[0]!= null)
			{	
				//Connexion du client
				CloseableHttpClient client = HttpClients.createDefault();
				String url = "http://"+args[0];
				HttpGet request = new HttpGet(url);
				

				//Réponse du serveur
				//System.out.println("Executing request" + request.getRequestLine());
				CloseableHttpResponse resp = client.execute(request);
				
				/*Affichage
				System.out.println( "Response Line : " + resp.getStatusLine());
				System.out.println("Response Code:" + resp.getStatusLine().getStatusCode());*/

				//Récupération des données JSON
				InputStreamReader isr = new InputStreamReader(resp.getEntity().getContent( ));
				
				//Lecteur JSON
				JsonReader reader = Json.createReader(isr) ;
				JsonObject jsonObject = reader.readObject();
				
				
				reader.close();
				isr.close();

				System.out.println( "duree=" + jsonObject.getString("Runtime"));
				

			}
			else{
				System.exit(1);
			}

		}
		catch( Exception ex ) {
			System.out.println( "erreur ! " );
			ex.printStackTrace();
			
		}

	}

}
