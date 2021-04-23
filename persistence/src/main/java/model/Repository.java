package model;

import javax.persistence.*;


@Entity
@Table( name = "repository" )
public class Repository
{
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY ) private int id;

    private String name;

    @ManyToOne @JoinColumn( name = "owner_id", nullable = false ) private User owner;


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


    public User getOwner()
    {
        return owner;
    }


    public void setOwner( User owner )
    {
        this.owner = owner;
    }
}
