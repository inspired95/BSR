package model;

public class Category
{
    public static Category OTHER_CATEGORY = new Category( "Other", new String[]{} );
    private String categoryName;
    private String[] keywords;

    public Category(String categoryName, String[] keywords){
        this.categoryName = categoryName;
        this.keywords = keywords;
    }

    public String getCategoryName()
    {
        return categoryName;
    }


    public String[] getKeywords()
    {
        return keywords;
    }
}
