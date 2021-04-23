package model;

import javax.persistence.*;


@Entity
@Table( name = "keyword" )
public class Keyword
{
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY ) private int id;

    private String name;

    @ManyToOne @JoinColumn( name = "categoryId", nullable = false ) private Category category;


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


    public Category getCategory()
    {
        return category;
    }


    public void setCategory( Category category )
    {
        this.category = category;
    }
}
