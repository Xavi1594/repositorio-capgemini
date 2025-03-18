package com.example.catalogo.aplication.contracts;

import java.sql.Timestamp;
import com.example.catalogo.aplication.models.NewsDTO;

public interface CatalogoService {
    NewsDTO newsDTO(Timestamp lastUpdate);
}
