package com.api.entitie;

public enum TypeObjet {
	
	amelioration("amelioration"), potion("potion"), composant("composant") ;  
    
    private String nom ;  
     
    private TypeObjet(String nom) {  
        this.nom = nom ;  
   }  
     
    public String getNom() {  
        return  this.nom ;  
   }  

}
