package com.ironhack.salesRepservice.controller.dtos;

import javax.validation.constraints.NotEmpty;

public class SalesRepDTO {
    private Integer id;
    @NotEmpty
    private String name;

    /*@OneToMany(mappedBy = "salesRep")
    List<Lead> leads;
    @OneToMany(mappedBy = "salesRep")
    List<Opportunity> opportunities;*/

    public SalesRepDTO() {
    }

    public SalesRepDTO(String name) {
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
