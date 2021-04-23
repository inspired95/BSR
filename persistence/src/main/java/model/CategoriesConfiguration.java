package model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table( name = "categoriesconfiguration" )
public class CategoriesConfiguration
{
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY ) private int id;

    @ManyToOne @JoinColumn( name = "ownerId", nullable = false ) private User owner;

    @OneToMany( mappedBy = "categoriesConfiguration" ) private Set<Category> categories;


    public int getId()
    {
        return id;
    }


    public void setId( int id )
    {
        this.id = id;
    }


    public User getOwner()
    {
        return owner;
    }


    public void setOwner( User owner )
    {
        this.owner = owner;
    }


    public Set<Category> getCategories()
    {
        return categories;
    }


    public void setCategories( Set<Category> categories )
    {
        this.categories = categories;
    }
}
