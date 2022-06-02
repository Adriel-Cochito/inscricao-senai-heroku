package com.senai.inscricao.web.conversor;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.senai.inscricao.domains.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
public class CriarCsvComStringsOpenCsv {

    public static void main(String[] args) throws IOException {
    	

        
        
        Writer writer1 = new FileWriter("yourfile.csv");
        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer1).build();
        
        
        
    }

}