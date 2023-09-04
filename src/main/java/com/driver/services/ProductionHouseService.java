package com.driver.services;
import java.util.*;


import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionHouseService {

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addProductionHouseToDb(ProductionHouseEntryDto productionHouseEntryDto){
             ProductionHouse productionHouse = new ProductionHouse();
             productionHouse.setName(productionHouseEntryDto.getName());
//             List<WebSeries> webseriesList = productionHouse.getWebSeriesList();
//             int rating = getRating(webseriesList);
             productionHouse.setRatings(0);
             productionHouseRepository.save(productionHouse);

        return  null;
    }

//    private int getRating(List<WebSeries> webSeriesList){
//        int cnt=0;
//        int SumOfratings=0;
//        for(WebSeries wb:webSeriesList){
//            SumOfratings+=wb.getRating();
//            cnt++;
//        }
//        return SumOfratings/cnt;
//    }



}
