package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class ReadFile {
	public static String getXMLString(String FilePath) throws IOException {
		FileInputStream fis = new FileInputStream(FilePath);
		byte[] buffer = new byte[10];
		StringBuilder sb = new StringBuilder();
		while (fis.read(buffer) != -1) {
		  	sb.append(new String(buffer));
		  	buffer = new byte[10];
		}
		fis.close();
		String content = sb.toString();
		//System.out.println(content);		  
		return content;
	}
	
	public static JSONObject getMyJSONObject(String XMLString) throws IOException, JSONException {
		JSONObject json = XML.toJSONObject(XMLString);
		//String jsonPrettyPrintString = json.toString(4); // json pretty print
        //System.out.println(jsonPrettyPrintString);		  
		return json;
	}
	
	public static ArrayList<FileArtifactsPair> getCloudletJobs(String FilePath) throws IOException, JSONException {
		String XMLString = getXMLString(FilePath);
		JSONObject json = getMyJSONObject(XMLString);
//		 String jsonPrettyPrintString = json.toString(); // json pretty print
//      System.out.println(jsonPrettyPrintString);
		
//		JSONObject Agad = json.getJSONObject("adag");
//		JSONArray Jobs = Agad.getJSONArray("job");		
//		JSONObject Job = Jobs.getJSONObject(0);
//		JSONArray uses = Job.getJSONArray("uses");
//		//System.out.println(uses.toString());
//		JSONObject t = new JSONObject(uses.get(0).toString());
//		
//		System.out.println(t.get("size")); //integer
		
      // something
		ArrayList<FileArtifactsPair> timeLine = new ArrayList<FileArtifactsPair>();
		JSONArray Jobs = json.getJSONObject("adag").getJSONArray("job");
		
		for(int i=0;i<Jobs.length();i++) {
			JSONObject Job = Jobs.getJSONObject(i);
			JSONArray uses = Job.getJSONArray("uses");
			
			int output = 0;
			int input = 0;
			//int cntIP = 0;
			
			for(int j=0;j<uses.length();j++) {
				JSONObject internal = new JSONObject(uses.get(0).toString());				
				if(internal.get("link").equals("output")) {
					output+=internal.getInt("size");
				}else {
					input+=internal.getInt("size");
					//cntIP++;
				}
			}
			
//			if(cntIP!=0) {
//				input/=cntIP;
//			}
			
			FileArtifactsPair cur = new FileArtifactsPair(input,output);
			
			timeLine.add(cur);
			
			
		}       

		         
         //return array		
		return timeLine;
		
	}

}
