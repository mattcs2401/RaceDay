package com.mcssoft.raceday.utility

import android.content.Context
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.InputStream
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class RaceDayParser(private var context: Context) {

    private lateinit var inStream: InputStream

    /**
     * Set the current input stream based upon a file id.
     * @param The file id.
     */
    fun setInputStream(fileId: Long) {
        inStream = RaceDownloadManager(context).getFile(fileId)
    }

    /**
     * Parse for all meetings.
     * @return A List<Map<LocalName, NodeValue>>.
     */
    fun parseForMeeting(): ArrayList<MutableMap<String, String>> {
        val expr = "/RaceDay/Meeting"
        return parse(expr)     // only one Meeting is expected.
    }

    /**
     * Parse for a specific <Race></Race> with a <Meeting></Meeting>.
     * @param meetingCode: The Meeting code, e.g. BR.
     * @return A Map<LocalName, NodeValue>.
     */
    fun parseForMeeting(meetingCode: String): MutableMap<String,String> {
        val expr = "/RaceDay/Meeting[@MeetingCode='$meetingCode']"
        return parse(expr)[0]     // only one Meeting is expected.
    }

    /**
     * Generic parse method. Will parse the given XPath expression into an ArrayList of Map.
     * @param xpathExpr: The XPath expression to parse on.
     * @return: An Array of Map<String,String> (Node LocalName and NodeValue).
     */
    private fun parse(xpathExpr: String): ArrayList<MutableMap<String, String>> {
        val inputSource = InputSource(inStream)
        val xpath = XPathFactory.newInstance().newXPath()

        val lNodes = xpath.evaluate(xpathExpr, inputSource, XPathConstants.NODESET) as NodeList
        val mapGet = mutableMapOf<String, String>()
        val lMap = ArrayList<MutableMap<String,String>>()

        if(lNodes.length > 0) {
            val len = lNodes.length
            for(ndx in 0..len) {
                val node = lNodes.item(ndx)
                if (node != null) {
                    val lNodeAttrs = node.attributes
                    for (ndx2 in 0 until lNodeAttrs.length) {
                        val attrNode = lNodeAttrs.item(ndx2)
                        mapGet[attrNode.localName] = attrNode.nodeValue
                    }
                    val mapPut = HashMap(mapGet)
                    lMap.add(mapPut)
                    mapGet.clear()
                }
            }
        }
        return lMap
    }
}