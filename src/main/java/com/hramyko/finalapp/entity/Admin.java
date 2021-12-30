package com.hramyko.finalapp.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends User {

}
