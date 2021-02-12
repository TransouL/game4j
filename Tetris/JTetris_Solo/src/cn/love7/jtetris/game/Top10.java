package cn.love7.jtetris.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Top10 {

	Player[] players = new Player[10];
	String filename = "top10";

	public Top10() {

		for (int i = 0; i < players.length; i++) {
			players[i] = new Player();
		}
	}

	// 是否上榜
	public boolean inTop10(int score) {
		if (score > players[9].getScore())
			return true;
		return false;
	}

	// 插入排行榜
	public void joinTop10(Player player) {

		for (int i = 0; i < players.length; i++) {
			if (player.getScore() > players[i].getScore()) {
				for (int j = players.length - 1; j > i; j--) {
					players[j].setScore(players[j - 1].getScore());
					players[j].setName(players[j - 1].getName());
				}
				players[i].setScore(player.getScore());
				players[i].setName(player.getName());
				return;
			}
		}
	}

	// 写入纪录文件
	public void write() {
		String whole = "";
		for (int i = 0; i < players.length; i++) {
			whole += players[i].toString();
		}

		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File file = new File(filename);
			if (!file.exists())
				file.createNewFile();
			fw = new FileWriter(filename); // 如果存在覆盖内容
			bw = new BufferedWriter(fw);
			bw.write(whole);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 读取纪录文件
	public void read() {
		File recordFile = new File(filename);
		if(!recordFile.isFile()) {
			return;
		}
		
		FileReader fileReader = null;
		BufferedReader br = null;
		try {
			fileReader = new FileReader(recordFile);
			br = new BufferedReader(fileReader);
			for (int i = 0; i < players.length; i++) {
				String line = br.readLine();
				if (line != null) {
					String[] parts = line.split("\t");
					players[i].setName(parts[0]);
					players[i].setScore(Integer.valueOf(parts[1]));
				} else
					break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String toString() {
		StringBuilder top10SB = new StringBuilder();
		for (int i = 0; i < players.length; i++) {
			top10SB.append(players[i].toString());
		}
		return top10SB.toString();
	}

}
