package e.deedcorpsinc.popularmovies.utilities;

public interface AsyncResponse {
    void processFinish(String output, int requestCode);
    void processFinish(String output);
}
