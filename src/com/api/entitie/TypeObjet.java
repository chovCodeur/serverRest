package com.api.entitie;

public enum TypeObjet {
	
	AMELIORATION("AMELIORATION"), POTION("POTION") ;  
    
    private String nom ;  
     
    private TypeObjet(String nom) {  
        this.nom = nom ;  
   }  
     
    public String getNom() {  
        return  this.nom ;  
   }  

}
