package model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table( name = "category" )
public class Category
{
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY ) private int id;

    private String name;

    @ManyToOne @JoinColumn( name = "categoriesConfigurationId", nullable = false ) private CategoriesConfiguration
        categoriesConfiguration;

    @OneToMany( mappedBy = "category" ) private Set<Keyword> keywords;


    public int getId()
    {
        return id;
    }


    public void setId( int id )
    {
        this.id = id;
    }


    public String getName()
    {
        return name;
    }


    public void setName( String name )
    {
        this.name = name;
    }


    public CategoriesConfiguration getCategoriesConfiguration()
    {
        return categoriesConfiguration;
    }


    public void setCategoriesConfiguration( CategoriesConfiguration categoriesConfiguration )
    {
        this.categoriesConfiguration = categoriesConfiguration;
    }


    public Set<Keyword> getKeywords()
    {
        return keywords;
    }


    public void setKeywords( Set<Keyword> keywords )
    {
        this.keywords = keywords;
    }
}
