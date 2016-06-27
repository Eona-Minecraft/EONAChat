package de.eonaminecraft.eonachat;


import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


/**
 * Filters bad words - obviously
 */
class BadWordsFilter
{

private static HashSet<String> badWords = new HashSet<String>();
private static ArrayList<String> niceWords = new ArrayList<String>();


static void loadFiles(File dataFolder)
{
	badWords.clear();
	niceWords.clear();

	try
	{
		File badWordsFile = new File(dataFolder.getPath() + File.separator + "badwords.txt");
		File niceWordsFile = new File(dataFolder.getPath() + File.separator + "nicewords.txt");

		//noinspection ResultOfMethodCallIgnored
		badWordsFile.mkdirs();

		if (badWordsFile.exists())
		{
			try
			{
				FileInputStream fstream = new FileInputStream(badWordsFile);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));

				String thisLine = br.readLine();
				int i = 0;
				while (thisLine != null)
				{
					badWords.add(thisLine);
					i++;
					thisLine = br.readLine();
				}
				br.close();
				in.close();
				fstream.close();
				EonaChat.log("Read " + i + " bad words from badwords.txt");
			} catch (Exception e)
			{
				EonaChat.log.warning("Could not create badwords.txt: " + e.getMessage());
			}
		} else
		{
			try
			{
				//noinspection ResultOfMethodCallIgnored
				badWordsFile.createNewFile();
			} catch (IOException e)
			{
				EonaChat.log.warning("Could not create badwords.txt: " + e.getMessage());
			}
		}
		if (niceWordsFile.exists())
		{
			try
			{
				FileInputStream fstream = new FileInputStream(niceWordsFile);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));

				String thisLine = br.readLine();
				int i = 0;
				while (thisLine != null)
				{
					niceWords.add(thisLine);
					i++;
					thisLine = br.readLine();
				}
				br.close();
				in.close();
				fstream.close();
				EonaChat.log("Read " + i + " nice words from nicewords.txt");
			} catch (Exception e)
			{
				EonaChat.log.warning("Could not create nicewords.txt: " + e.getMessage());
			}
		} else
		{
			try
			{
				//noinspection ResultOfMethodCallIgnored
				niceWordsFile.createNewFile();
			} catch (IOException e)
			{
				EonaChat.log.warning("Could not create nicewords.txt: " + e.getMessage());
			}
		}
	} catch (SecurityException e)
	{
		EonaChat.log.warning("Could not create data folder: " + e.getMessage());
	}
}


static String filter(String message)
{
	if (badWords.size() != 0)
	{
		if (niceWords.size() != 0)
		{
			String[] words = message.split(" ");
			message = "";
			for (String word : words)
			{
				if (badWords.contains(word.toLowerCase()))
				{
					message += " " + niceWords.get(new Random().nextInt(niceWords.size()));
				} else
				{
					message += " " + word;
				}
			}
			return message.trim();
		} else
		{
			EonaChat.log("No nice Words defined, add them to nicewords.txt in plugin directory!");
			return message;
		}
	} else
	{
		EonaChat.log("No bad Words defined, add them to badwords.txt in plugin directory!");
		return message;
	}
}
}
