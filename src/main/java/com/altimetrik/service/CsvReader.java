package com.altimetrik.service;

import com.altimetrik.constants.FilePathProvider;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CsvReader {
    public <T> List<T> loadObjectList(Class<T> type) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            File file = new File(FilePathProvider.getPath());
            MappingIterator<T> readValues =
                    mapper.reader(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file", e);
            return Collections.emptyList();
        }
    }
}
