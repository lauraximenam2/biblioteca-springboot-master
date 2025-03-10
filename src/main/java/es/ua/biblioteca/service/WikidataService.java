package es.ua.biblioteca.service;

import java.io.ByteArrayOutputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.springframework.stereotype.Service;
import org.apache.jena.rdf.model.ModelFactory ;

@Service
public class WikidataService {
	
	public static String sparqlRepository = "https://query.wikidata.org/sparql";

	public String getFilms(int num) {

		String resultado = "";
		
		String queryString =
				"PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
				+ "PREFIX wd: <http://www.wikidata.org/entity/> "
				+ "PREFIX wikibase: <http://wikiba.se/ontology#> "
				+ "PREFIX bd: <http://www.bigdata.com/rdf#> "
				//Query añadida para films
				+ "SELECT * WHERE { "
				+ "   SERVICE <https://query.wikidata.org/sparql> { "
				+ "       SELECT DISTINCT ?pelicula ?peliculaLabel ?anio "
				+ "       WHERE { "
				+ "           ?pelicula wdt:P31 wd:Q11424; " // Instancia de película
				+ "                     wdt:P495 wd:Q29; "  // País de origen: España
				+ "                     wdt:P577 ?fecha. "  // Fecha de publicación
				+ "           BIND(YEAR(?fecha) AS ?anio) "
				+ "           SERVICE wikibase:label { bd:serviceParam wikibase:language 'es' } "
				+ "       } ORDER BY DESC(?anio) "
				+ "       LIMIT " + num
				+ "   }"
				+ " }";

//		        + "SELECT * WHERE { "
//				+ "   SERVICE <https://query.wikidata.org/sparql> { "
//						+ "SELECT DISTINCT ?pelicula ?peliculaLabel ?anio "
//						+ "WHERE { "
//						+ "  ?pelicula wdt:P31 wd:Q11424; " // Instancia de película
//						+ "            wdt:P495 wd:Q29; "   // País de origen: España
//						+ "            wdt:P577 ?fecha. "   // Fecha de publicación
//						+ "  BIND(YEAR(?fecha) AS ?anio) "
//						+ "  SERVICE wikibase:label { bd:serviceParam wikibase:language 'es' } "
//						+ "} ORDER BY DESC(?anio) "
//						+ "LIMIT " + num
//				+ "   }"
//				+ " }";

		Query query = QueryFactory.create(queryString) ;
		try (QueryExecution qexec = QueryExecutionFactory.create(query, ModelFactory.createDefaultModel())) {
			ResultSet rs = qexec.execSelect() ;
			//ResultSetFormatter.out(System.out, rs, query) ;

			// write to a ByteArrayOutputStream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			ResultSetFormatter.outputAsJSON(outputStream, rs);

			// and turn that into a String
			resultado = new String(outputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace(); // Imprimimos el error en logs
			return "{\"error\": \"Error en la consulta SPARQL\"}";
		}

        return resultado;
	}
}