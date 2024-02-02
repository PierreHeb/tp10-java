import java.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import javax.json.*;
import java.util.Scanner;


public class Client3
{

	public static void main(String[] args)
	{
		try{
		     while(true){
				
				Scanner sc= new Scanner(System.in); //Creation du reader de la saisie utilisateur
				System.out.println("\nVeuillez saisir un nom de film :"); 
				String titre = sc.nextLine(); //Saisie utilisateur
				
				
				/* On peut ici faire saisir l'année à l'utilisateur au besoin
				Scanner sc2= new Scanner(System.in);
				System.out.println("Veuillez saisir l'annee :"); 
				String annee = sc2.nextLine();*/
				
				//Test de la saisie utilisateur
				if( titre!= null)
				{	
					//Connexion du client
					CloseableHttpClient client = HttpClients.createDefault();
					String url = "http://www.omdbapi.com/?apikey=751ea6aa&t="+titre;
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
					
					//On clot le reader et le flux
					reader.close();
					isr.close();
					

					
					//On test ici si la réponse json contient la String "False"
					if(jsonObject.getString("Response").contains("False"))
					{
						//On affiche une erreur
						System.out.println( "erreur de saisie, le film que vous cherchez n'existe pas");
					}
					else
					{	
						//On récupère le tableau d'objets "Ratings" dans la requête json
						JsonArray table = jsonObject.getJsonArray("Ratings");
						
						//On boucle sur le tableau
						for( int i=0; i<table.size(); i++ )
						{
							//Ici on récupère chaque tableau que contient "Ratings" et qui contient 2 valeurs
							//Source et Value
							JsonObject ji = table.getJsonObject(i);
							
							//On test si la valeur de "Source" contient "Rotten Tomatoes"
							if (ji.getString("Source").contains("Rotten Tomatoes"))
							{
								//On récupère ensuite la valeur dans "Value" et l'affiche
								String ratingPercent = ji.getString("Value");
								
								String ratingNotPercent = ratingPercent.replaceAll("%","");
								int ratingToInt = Integer.parseInt(ratingNotPercent);
								
								if(ratingToInt >70)
								{
									System.out.println("Rotten Tomatoes Critiques: Très bien");
								}
								else if(ratingToInt >50 || ratingToInt <=70)
								{
									System.out.println("Rotten Tomatoes Critiques: Bien");

								}
								else if(ratingToInt >=20 || ratingToInt <=50)
								{
									System.out.println("Rotten Tomatoes Critiques: Bof");
								}

								else if(ratingToInt <20)
								{
									System.out.println("Rotten Tomatoes Critiques: Nul");
								}
									
							}
							else{}
						}

						//On affiche la différentes informations sur le film
						System.out.println( "Date de sortie: " + jsonObject.getString("Year"));	
						System.out.println( "Acteurs: " + jsonObject.getString("Actors"));
						
						
					}
				

				}
			
				else{
					System.exit(1);
				}
			}

		}
		catch( Exception ex ) {
			System.out.println( "erreur ! " );
			ex.printStackTrace();
			
		}

	}

}
