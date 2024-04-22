package com.example.finalProjectDesignPatterns.repository;



import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.logger.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.orm.jpa.JpaSystemException;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

    Logger logger = Logger.getInstance();

    default ResponseEntity<T> saveAndCatch(T t) {
        ResponseEntity<T> responseEntity = null;
        try{
            T savedT = save(t);
            logger.info("Entity added successfully" + savedT);
            responseEntity = new ResponseEntity<>("Entity added successfully",savedT, ReturnType.SUCCESS);
        }catch (DataIntegrityViolationException e){
            responseEntity = new ResponseEntity<>("Data integrity violation, check the data and save again",t, ReturnType.FAILURE);
            logger.debug("Data integrity violation for " + t);
            logger.debug(e.getMessage());
        }catch( JpaSystemException e){
            responseEntity = new ResponseEntity<>("System Error,Failed to save data to the database, please try again after some time",null, ReturnType.FAILURE);
            logger.error("Failed connect to the database");
        }catch (Exception e){
            responseEntity = new ResponseEntity<>("System Error,please try again after some time",null, ReturnType.FAILURE);
            logger.error("Failed to save the entity");
        }
        return responseEntity;
    }

}
