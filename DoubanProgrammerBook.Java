import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Character.UnicodeBlock;

public class DoubanProgrammerBook
{
	public static String getUrlContent(String urlStr)
	{
		StringBuilder sb=new StringBuilder();
		try 
		{
			URL url=new URL(urlStr);

			try 
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String temp="";
				
				while((temp=reader.readLine())!=null){
					sb.append(temp);
				}
			}
	        	catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}

		return sb.toString();

	}

	public static List getMacher(String str,String regex) throws Exception
	{
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(str);
		List result=new ArrayList();

		while(m.find())
		{	
			String curLine = m.group(0);
			curLine = convertToGbk(curLine);
			if(curLine.contains("±à³Ì"))
				result.add(convertToGbk(m.group(1)));
		}

		return result;
	}

	public static String convertToGbk(String str) throws Exception
	{
		String result;
		String strUtf8;
		
		strUtf8 = new String(str.getBytes(),"UTF-8");//°Ñ×Ö·û´®strµÄ±àÂë×ª»»³ÉJavaÄ¬ÈÏµÄ±àÂëUTF-8
		result = new String(strUtf8.getBytes("GBK"));//°Ñ×Ö·û´®strUtf8µÄ±àÂë×ª»»³ÉGBK±àÂë
		
		return result;
	}
	

	public static void main(String args[])
	{
		String startUrlContent = getUrlContent("https://www.douban.com/");

		try
		{
			List list=getMacher(startUrlContent,"href=\"(.+?)\">(.+?)</a>");

			for(Object temp : list)
			{
				String t = (String)temp;
				System.out.println(t+"\r\n");
				
				String programBookUrlContent = getUrlContent(t);
				System.out.println(convertToGbk(programBookUrlContent) + "\r\n");
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}

