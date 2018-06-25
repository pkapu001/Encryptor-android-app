package encriptor.dragon.encriptor;

import android.content.res.AssetManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.InputStream;

final public class morse {

	String c;
	String symbol;



	public static void load() throws FileNotFoundException {




		Scanner f = new Scanner(MainActivity.is);
		// int i=0;
		String s;
		String[] ss;
		while (f.hasNext()) {
			morse temp = new morse();
			//String t="";
			s = f.nextLine();

			ss = s.split(" ");
			//t = ss[0];
			temp.c = ss[0];

			temp.symbol = ss[1];
			MainActivity.mkey.add(temp);
			// System.out.println(mkey.get(i).c + mkey.get(i).symbol );

			// i++;

		}
		f.close();
	}

	public static String e_morse(String code, ArrayList<morse> mkey) {
		String[] ch;
		String temp = "", msg = "";
		boolean space_check=true;

		ch = code.split("");
		for (String each : ch) {

			temp = "";
			for (morse m : mkey) {
				if (m.c.equals(each)) {
					temp = m.symbol + "   ";
					space_check=true;
					break;
				}else {
					temp="";
				}
			}
			//System.out.println("...");
			if (temp == "") {
				//System.out.println("..." + each + "..." + each.equals(" "));
				if (each.equals(" ")) {
					//System.out.println("+| ");
					if(space_check)
					{
						temp = "| ";
						space_check=false;
					}

				} else if(each.equals("\n")) {
					space_check=true;
					temp =  each + " | " ;
				}else{
					temp =" " + each + " ";
					space_check=true;
				}
			}
			msg += temp;
			System.out.println(msg);
		}
		return msg;
	}

	public static String d_morse(String code, ArrayList<morse> mkey) {
		String[] sy;
		boolean space_check=true;
		String temp = "" ,msg="";
		sy = code.split("[\" \"]");
		//sy = code.split(" ");
		for (String each : sy) {
			temp="";


			if (each.equals("|")) {
				if(space_check)
				{
					temp = " ";
					space_check=false;
				}

			} else {
				for (morse m : mkey) {
					if (m.symbol.equals(each)) {
						temp = m.c;
						space_check=true;
						break;
					}

				}
				if(temp=="")
				{
                    if(each.equals("\n"))
                    {
                        space_check = false;
                    }else{
                        space_check=true;
                    }

					temp=each;

				}
			}
			msg += temp;
		}
		return msg;
	}

}
