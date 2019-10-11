package solution.Assignment2;

import java.util.List;
import java.util.Scanner;

/**
 *

 */
public class MAIN {


    public static void main(String [] args) {
        ReviewHandler rh = new ReviewHandler();

        int realClass = 0;

        // first load from serialized file if present
        rh.loadSerialDB();

        Scanner sc = new Scanner(System.in);

        // display and loop with choices
        while(true) {
            switch(menu(sc)) {

                // exit after saving db
                case 0:
                    sc.close();
                    rh.saveSerialDB();
                    System.exit(-1);
                    break;

                // ask user for path and realclass
                case 1:
                    System.out.println("Enter folder/path:");
                    String filePath = sc.nextLine();
                    System.out.println("Enter real class of the review collection (0-Neg, 1-Pos, 2-Unknown):");
                    realClass = Integer.parseInt(sc.nextLine());
                    rh.loadReviews(filePath, realClass);
                    rh.classifyReviews();
                    rh.reportAccuracy(realClass);
                    rh.saveSerialDB();
                    break;

                // delete review if present based on id
                case 2:
                    System.out.println("Enter review id:");
                    int id = Integer.parseInt(sc.nextLine());
                    rh.deleteReview(id);
                    break;

                // search based on id or string and display results
                case 3:
                    System.out.println("Enter id or substring to search for:");
                    if(sc.hasNextInt()) {
                        id = Integer.parseInt(sc.nextLine());
                        MOVIEREVIEWER mr = rh.searchById(id);
                        System.out.println("reviewId\tText\tPredictedClass\tRealClass");
                        if(mr!=null) {
                            String text = mr.getText().length() > 50 ? mr.getText().substring(0,50) : mr.getText();
                            System.out.println(mr.getId() + "\t" + text + "\t" + mr.getPredictedPolarity() + "\t" + mr.getRealPolarity());
                        }
                        else {
                            System.out.println("No matching reviews");
                        }
                    }
                    else {
                        String sstr = sc.nextLine();
                        System.out.println("reviewId\tText\tPredictedClass\tRealClass");
                        List<MOVIEREVIEWER> mrs = rh.searchBySubstring(sstr);
                        for(MOVIEREVIEWER mr : mrs) {
                            String text = mr.getText().length() > 50 ? mr.getText().substring(0,50) : mr.getText();

                            System.out.println(mr.getId() + "\t\t" + text + "\t\t" + mr.getPredictedPolarity() + "\t\t" + mr.getRealPolarity());
                        }
                    }
                    break;
            }
        }
    }

    // displays static menu
    public static int menu(Scanner sc) {
        System.out.println("\n0. Exit program");
        System.out.println("1. Load new movie review collection (given a folder or a file path).");
        System.out.println("2. Delete movie review from database (given its id).");
        System.out.println("3. Search movie reviews in database by id or by matching a substring.");
        System.out.println("Enter choice:");
        int choice = Integer.parseInt(sc.nextLine());
        return choice;
    }


}
