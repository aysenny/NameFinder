import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BIMHW {
	public static void main(String[] args) throws IOException {

		String url="https://opennlp.apache.org/books-tutorials-and-talks.html";

		//gets the documents of the web page by using Jsoup
		Document doc= Jsoup.connect(url).get();

		//gets the part of a body text from the doc
		String text=doc.body().text();

		//Now, we have a text body from an url, and we need to read the names in that body by using tokenizer OpenNLP

		TokenNameFinderModel model = null;
		try {
			model = new TokenNameFinderModel(
					new File("src/main/resources/en-ner-person.bin"));
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//finder instance for finding the names in the text body
		NameFinderME finder= new NameFinderME(model);
		Tokenizer tokenizer= SimpleTokenizer.INSTANCE;
		//we add the tokens to the String array to hold
		String tokens[]= tokenizer.tokenize(text);
		// now, we should span the names
		Span [] spans= finder.find(tokens);
		String [] spanned_names=Span.spansToStrings(spans,tokens);

		for(String names:spanned_names){
			System.out.println(names);
		}






	}
}