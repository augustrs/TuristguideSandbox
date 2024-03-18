public class Main {

    public static void main(String[] args) {
        Repository repository = new Repository();
        //repository.deleteTouristAttractionFromListSQL("Den blå planet");
        //repository.createTouristAttractionOnListSQL("Den blå planet", "En stor blå planet", "Femøren");
        //repository.addTagOnAttraction("Den blå planet", "Børn");
        //repository.updateName("Den blå planet", "Den grønne planet");
        //repository.updateDescription("Den grønne planet", "En stor grøn planet");
        //repository.updateLocation("Den grønne planet", "København");
        repository.getAttractionAsObject();
        //repository.getTagsForAttraction(1);
    }
}
