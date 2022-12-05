package com.example.htmlToPdf;


import lombok.Data;

@Data
public class RequestDto {
	
	String nama;
	String umur;
	String pekerjaan;
	String kesukaan;
	Order order;
	
	@Data
	public class Order {
	    String waybill;
	    Shipper shipper;
	    
	    @Data
	    public class Shipper {
	        String name;
	        String phone;
	    }
	}

}
