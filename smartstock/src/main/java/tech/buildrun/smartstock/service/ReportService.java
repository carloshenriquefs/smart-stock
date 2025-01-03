package tech.buildrun.smartstock.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import tech.buildrun.smartstock.domain.CsvStockItem;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ReportService {

    public List<CsvStockItem> readStockReport(String reporthPath) throws IOException {

        try (Reader reader = Files.newBufferedReader(Paths.get(reporthPath))) {

            var builder = new CsvToBeanBuilder<CsvStockItem>(reader)
                    .withType(CsvStockItem.class)
                    .build();

            return builder.parse();
        }
    }
}
