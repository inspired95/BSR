package model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table( name = "user" )
public class User
{
    @Id @GeneratedValue( strategy = GenerationType.IDENTITY ) private int id;

    private String login;

    private String password;

    @OneToMany( mappedBy = "owner" ) private Set<CategoriesConfiguration> categoriesConfigurations;

    @OneToMany( mappedBy = "owner" ) private Set<Repository> repositories;


    public int getId()
    {
        return id;
    }


    public void setId( int id )
    {
        this.id = id;
    }


    public String getLogin()
    {
        return login;
    }


    public void setLogin( String login )
    {
        this.login = login;
    }


    public String getPassword()
    {
        return password;
    }


    public void setPassword( String password )
    {
        this.password = password;
    }


    public Set<CategoriesConfiguration> getCategoriesConfigurations()
    {
        return categoriesConfigurations;
    }


    public void setCategoriesConfigurations(
        Set<CategoriesConfiguration> categoriesConfigurations )
    {
        this.categoriesConfigurations = categoriesConfigurations;
    }


    public Set<Repository> getRepositories()
    {
        return repositories;
    }


    public void setRepositories( Set<Repository> repositories )
    {
        this.repositories = repositories;
    }
}
