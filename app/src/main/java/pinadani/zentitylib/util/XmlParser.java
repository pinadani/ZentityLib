package pinadani.zentitylib.util;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import pinadani.zentitylib.model.Book;

/**
 * XML parser to parse Zentity library
 * Created by Daniel Pina on 7/5/2017.
 */
public class XmlParser {

    private static String ZREADER_ROOT = "ZREADER";
    private static String BOOK = "BOOK";
    private static String ID_BOOK = "ID";
    private static String TITLE_BOOK = "TITLE";
    private static String THUMBNAIL_BOOK = "THUMBNAIL";
    private static String NEW_BOOK = "NEW";
    private static String THUMB_EXT_BOOK = "THUMB_EXT";

    public ArrayList<Book> parse(InputStream in) throws XmlPullParserException, IOException {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            return readLib(parser);
        } finally {
            in.close();
        }
    }

    /**
     * Library parsing. Adding individual books.
     */
    private ArrayList<Book> readLib(XmlPullParser parser) throws XmlPullParserException, IOException {

        ArrayList<Book> list = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            list.add(readBook(parser));
        }
        return list;
    }

    /**
     * Parse the book and create an instance.
     */
    private Book readBook(XmlPullParser parser) throws XmlPullParserException, IOException {

        String id = null;
        String title = null;
        String thumbnail = null;
        boolean newItem = false;
        String thumbExt = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals(ID_BOOK)) {
                id = readInnerText(parser);
            } else {
                if (name.equals(TITLE_BOOK)) {
                    title = readInnerText(parser);
                } else {
                    if (name.equals(THUMBNAIL_BOOK)) {
                        thumbnail = readInnerText(parser);
                    } else {
                        if (name.equals(NEW_BOOK)) {
                            newItem = Boolean.valueOf(readInnerText(parser));
                        } else {
                            if (name.equals(THUMB_EXT_BOOK)) {
                                thumbExt = readInnerText(parser);
                            } else {
                                skip(parser);
                            }
                        }
                    }
                }
            }
        }
        return new Book(id, title, thumbnail, newItem, thumbExt);
    }

    /**
     * read string from xml item
     */
    private String readInnerText(XmlPullParser parser) throws IOException, XmlPullParserException {
        return readText(parser);
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /**
     * Skip the next item.
     */
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
