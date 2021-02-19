package com.ironhack.salesRepservice.model;

import javax.persistence.*;

@Entity
public class SalesRep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    /*@OneToMany(mappedBy = "salesRep")
    List<Lead> leads;
    @OneToMany(mappedBy = "salesRep")
    List<Opportunity> opportunities;*/

    public SalesRep() {
    }

    public SalesRep(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
