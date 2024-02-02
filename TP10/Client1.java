import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;


public class Client1
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
				System.out.println("Executing request" + request.getRequestLine());
				CloseableHttpResponse resp = client.execute(request);
				
				/*Affichage
				System.out.println( "Response Line : " + resp.getStatusLine());
				System.out.println("Response Code:" + resp.getStatusLine().getStatusCode());*/

				//Récupération des données JSON
				BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

				//Lecture du HTML
				StringBuffer result = new StringBuffer();
				String line = " ";
				while ((line=rd.readLine())!=null)
				{
					result.append(line);
					result.append("\n"); // pour avoir le saut de ligne
				}
				//Affichage du résultat				
				String page = result . toString ( ) ;
				System . out . println( page ) ;


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
