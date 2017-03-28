package com.thedionisio.model.ctrl;

import com.thedionisio.dao.PersonRepository;
import com.thedionisio.model.bss.PersonBss;
import com.thedionisio.model.dto.Person;
import com.thedionisio.util.validation.RequestValidation;
import org.bson.Document;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by jonathan on 3/2/17.
 */

public class PersonCtrl {

    private RequestValidation validation = new RequestValidation();
    private PersonBss personBss = new PersonBss();
    private PersonRepository personRepository = new PersonRepository();
    private final String collection  ="person";

    public Object create(Person person){

        try
        {
            if(personBss.createValidation(person))
            {
              if (personBss.existingValidation(person))
              {
                  ResponseEntity responseEntity = personRepository.create(collection, personBss.treatCreate(person));

                  try
                  {
                      if (Boolean.parseBoolean(responseEntity.getBody().toString()))
                      {
                          return validation.registry_create(person._id);
                      }
                  }
                  catch (Exception e)
                  {
                      return responseEntity;
                  }

              }
              return validation.registry_existed("email <" + person.email);
            }
            return validation.not_contains_fields(person.isRequered());
        }
        catch (Exception e)
        {
            return validation.not_data_base();
        }

    }

    public Object find(){

        Object objectResponse = personRepository.find(collection,new Person(), new Document(), new Document());

        try
        {
            ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) objectResponse;
            List<Person> person = (List<Person>) responseEntity.getBody();

            return personBss.treatResponse(person);
        }
        catch (Exception e)
        {
            return objectResponse;
        }


    }

    public Object findOne(Object id){
        try
        {
            if(validation.idValidation(id))
            {
                Object objectResponse = personRepository.findOne(collection,id, new Person());
                ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) objectResponse;
                List<Person> person = (List<Person>) responseEntity.getBody();
                return personBss.treatResponse(person);
            }
            return validation.not_contains_id();
        }
        catch (Exception e)
        {
            return validation.not_data_base();
        }
    }

    public Object update(Person person){

       try
       {
           if(validation.idValidation(person._id))
           {
               Object objectResponse = personRepository.update(collection, person._id, person);
              try
              {
                  if((Boolean) objectResponse)
                  {
                      return validation.registry_create(person._id);
                  }
              }catch (Exception e)
              {
                  return objectResponse;
              }

           }
           return validation.not_contains_id();
       }
       catch (Exception e)
       {
           return validation.not_data_base();
       }
    }

    public Object remove(Person person){
       try
       {
           if(validation.idValidation(person._id))
           {
               Object objectResponse = personRepository.removeOne(collection, person._id);

               ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) objectResponse;

               try
               {
                    if ((Boolean)responseEntity.getBody())
                    {
                        return  validation.registry_deleted(person._id);
                    }
               }
               catch (Exception e)
               {
                   return responseEntity;
               }
           }
           return validation.not_contains_id();
       }
       catch (Exception e)
       {
           return validation.not_data_base();
       }

    }
}
