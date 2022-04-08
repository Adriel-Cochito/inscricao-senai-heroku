package com.senai.inscricao.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;

public class TesteAlgoritmo {
	public static void main(String[] args) {

        String helloWorld = "Hello World!";

        helloWorld = Base64.encodeBase64String(helloWorld.getBytes());

     System.out.println("String codificada " + helloWorld);

     //
     // Decodifica uma string anteriormente codificada usando o método decodeBase64 e
     // passando o byte[] da string codificada
     //
     byte[] decoded = Base64.decodeBase64(helloWorld.getBytes());

     //
     // Imprime o array decodificado
     //
     System.out.println(Arrays.toString(decoded));

     //
     // Converte o byte[] decodificado de volta para a string original e imprime
     // o resultado.
     //
     String decodedString = new String(decoded);
     System.out.println(helloWorld + " = " + decodedString);
   }

}