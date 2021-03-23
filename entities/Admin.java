package com.Bootcamp.Project.Application.entities;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Component
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User{
}
