package solution.Assignment2;

import movieReviewClassification.AbstractReviewHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ReviewHandler extends AbstractReviewHandler {


    @Override
    public void loadReviews(String filePath, int realClass) {

        try {
            File fp = new File(filePath);
            if (fp.exists() && !fp.isDirectory()) {
                solution.Assignment2.MOVIEREVIEWER mr = readReview(filePath,realClass);
                database.put(database.keySet().size(), mr);
            }
            else {
                ArrayList<String> posFiles = populateFileNames(filePath, "txt");
                for(String tmpPath: posFiles) {
                    solution.Assignment2.MOVIEREVIEWER mr = readReview(tmpPath,realClass);
                    database.put(database.keySet().size(), mr);
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public solution.Assignment2.MOVIEREVIEWER readReview(String reviewFilePath, int realClass) throws IOException {

        Scanner reviewScanner = new Scanner(new File(reviewFilePath));

        int id = database.size();
        String text = reviewScanner.nextLine();
        reviewScanner.close();
        solution.Assignment2.MOVIEREVIEWER mr = new solution.Assignment2.MOVIEREVIEWER(id,text,realClass);
        classifyReview(mr);
        return mr;
    }

    @Override
    public void deleteReview(int id) {
        if(database.containsKey(id))
            database.remove(id);
    }

    @Override
    public void loadSerialDB() {

        File serFile = new File(DATA_FILE_NAME);
        if(serFile.exists()) {

            FileInputStream fis;
            try {
                fis = new FileInputStream(DATA_FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fis);
                database = (HashMap<Integer, solution.Assignment2.MovieReview>)ois.readObject();
                ois.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    @Override
    public solution.Assignment2.MOVIEREVIEWER searchById(int id) {
        if(database.containsKey(id))
            return database.get(id);
        return null;
    }

    @Override
    public List<solution.Assignment2.MOVIEREVIEWER> searchBySubstring(String substring) {

        List<solution.Assignment2.MOVIEREVIEWER> mrs = new ArrayList<solution.Assignment2.MOVIEREVIEWER>();
        for(solution.Assignment2.MOVIEREVIEWER mr : database.values()) {
            if (mr.getText().contains(substring))
                mrs.add(mr);
        }


        return mrs;
    }


    public static ArrayList<String> populateFileNames(String fFolder, String fExt) {

        ArrayList<String> fList = new ArrayList<String>();
        File fileDir = new File(fFolder);
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            return null;
        }
        String[] files = fileDir.list();
        if (files.length == 0) {
            return null;
        }

        // Get the file separator character for this operating system
        //("/" for Linux and Mac, "\" for Windows).
        String fileSeparatorChar = System.getProperty("file.separator");

        for (int i = 0; i < files.length; i++) {
            if (fExt == null || files[i].endsWith(fExt)) {
                fList.add(fFolder + fileSeparatorChar + files[i]);
            }
        }

        return fList;
    }

    public void classifyReviews() {
        for (solution.Assignment2.MOVIEREVIEWER mr : database.values()) {
            classifyReview(mr);
        }
    }

    public void reportAccuracy(int realClass) {
        if(realClass ==2) return;

        int total = database.values().size();
        int count = 0;
        for (solution.Assignment2.MOVIEREVIEWER mr : database.values()) {
            int review = mr.getPredictedPolarity();
            if (review == realClass)
                count++;
        }
        System.out.println("Accuracy of classification: " + count*100.0/total + " %");
    }

}