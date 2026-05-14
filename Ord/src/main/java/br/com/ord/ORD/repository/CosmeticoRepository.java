package br.com.ord.ORD.repository;

import br.com.ord.ORD.model.Cosmetico;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CosmeticoRepository extends MongoRepository<Cosmetico,String> {
}
