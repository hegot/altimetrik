package com.altimetrik.service;


import com.altimetrik.constants.FilePathProvider;
import com.altimetrik.domain.Payment;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Slf4j
@Service
public class CsvWriter {


    public void writePayments(List<Payment> payment) {
        try {
            Writer writer = new FileWriter(FilePathProvider.getPath());
            CSVWriter CSVwriter = new CSVWriter(writer);
            ColumnPositionMappingStrategy<Payment> mapStrategy
                    = new ColumnPositionMappingStrategy<>();
            mapStrategy.setType(Payment.class);

            String[] columns = new String[]{"id", "amount", "currency", "user", "targetAccount"};
            mapStrategy.setColumnMapping(columns);
            CSVwriter.writeNext(columns);
            StatefulBeanToCsv<Payment> btcsv = new StatefulBeanToCsvBuilder<Payment>(CSVwriter)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    .withSeparator(',')
                    .build();

            btcsv.write(payment);
            writer.close();
        } catch (CsvException | FileNotFoundException ex) {

            log.error("Error mapping Bean to CSV", ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}