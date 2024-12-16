package com.pharmacy.modules.inventory.service;


import com.pharmacy.config.ApiException;
import com.pharmacy.config.FieldValidator;
import com.pharmacy.modules.approved_medicines.model.FdaApprovedMedicine;
import com.pharmacy.modules.approved_medicines.model.Openfda;
import com.pharmacy.modules.approved_medicines.service.ApiFdaService;
import com.pharmacy.modules.inventory.model.Category;
import com.pharmacy.modules.inventory.model.Medicine;
import com.pharmacy.modules.inventory.model.Supplier;
import com.pharmacy.modules.inventory.repository.CategoryRepository;
import com.pharmacy.modules.inventory.repository.MedicineRepository;
import com.pharmacy.modules.inventory.repository.SupplierRepository;
import com.pharmacy.payload.AddMedicineRequest;
import com.pharmacy.payload.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final FieldValidator fieldValidator;
    private final ApiFdaService apiFdaService;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;

    public Response<?> deleteMedicine(Long id){
        log.info("DeleteMedicine start.");
        Optional<Medicine> medicine = medicineRepository.findById(id);
        if(medicine.isPresent()){
            medicineRepository.delete(medicine.get());
            log.info("Medicine deleted successfully");
            Response<Medicine> response =new Response<>();
            response.setStatus(Response.Status.SUCCESS);
            return response;
        }else{
            String errMessage = String.format("Medicine doesn't exists.");
            log.info(errMessage);
            Response<Medicine> response =new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage(errMessage);
            return response;
        }
    }

    public Response<?> changePrice(Long medicineId, Long newPrice){
        log.info("ChangePrice start. Medicine Id = {}", medicineId);
        log.info("New price: {}", newPrice);
        Optional<Medicine> medicineOpt = medicineRepository.findById(medicineId);
        if(newPrice < 0){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Negative numbers not allowed.");
        }
        if(medicineOpt.isEmpty()) {
            log.info("Medicine with id: " + medicineId + " doesn't exists.");
            Response response = new Response();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("Medicine doesn't exist.");
            return response;
        }
        Medicine medicine = medicineOpt.get();
        medicine.setPrice(BigDecimal.valueOf(newPrice));
        medicineRepository.save(medicine);
        log.info("Medicine saved successfully. New price set to {}", newPrice);
        Response response = new Response();
        response.setStatus(Response.Status.SUCCESS);
        response.setMessage("Price modified successfully.");
        return response;
    }

    public Response<?> decreaseStock(Long medicineId, Long decreaseBy){
        log.info("DecreaseStock start. Medicine Id = {}", medicineId);
        log.info("DecreaseStock stock by: {}", decreaseBy);
        Optional<Medicine> medicineOpt = medicineRepository.findById(medicineId);
        if(decreaseBy < 1){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Negative numbers not allowed.");
        }
        if(medicineOpt.isEmpty()) {
            log.info("Medicine with id: " + medicineId + " doesn't exists.");
            Response response = new Response();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("Medicine doesn't exist.");
            return response;
        }
        Medicine medicine = medicineOpt.get();
        Long actualStock = medicine.getStock();
        Long newStock = actualStock - decreaseBy;
        if(newStock < 0){
            throw new ApiException(HttpStatus.BAD_REQUEST, "New stock cannot be negative.");
        }
        medicine.setStock(newStock);
        medicineRepository.save(medicine);
        log.info("Medicine saved successfully. New stock set to {}", newStock);
        Response response = new Response();
        response.setStatus(Response.Status.SUCCESS);
        response.setMessage("Stock increased successfully.");
        return response;
    }

    public Response<?> increaseStock(Long medicineId, Long increaseBy){
        log.info("IncreaseStock start. Medicine Id = {}", medicineId);
        log.info("Increase stock by: {}", increaseBy);
        Optional<Medicine> medicineOpt = medicineRepository.findById(medicineId);
        if(increaseBy < 1){
            throw new ApiException(HttpStatus.BAD_REQUEST, "Negative numbers not allowed.");
        }
        if(medicineOpt.isEmpty()) {
            log.info("Medicine with id: " + medicineId + " doesn't exists.");
            Response response = new Response();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("Medicine doesn't exist.");
            return response;
        }
        Medicine medicine = medicineOpt.get();
        Long actualStock = medicine.getStock();
        Long newStock = actualStock + increaseBy;
        medicine.setStock(newStock);
        medicineRepository.save(medicine);
        log.info("Medicine saved successfully. New stock set to {}", newStock);
        Response response = new Response();
        response.setStatus(Response.Status.SUCCESS);
        response.setMessage("Stock increased successfully.");
        return response;
    }

    /**
     * We can ADD only FDA approved medicines, so we need to search for them by using the public exposed api by them.
     * Steps:
     * 1. validate the mandatory fields.
     * 2. check if it is an approved medicine by FDA
     * 3. check the FK if exists in table.
     * 4. check the stock to be >0
     * 5. create the fk objects to assign to the Medicine class
     */
    public Response<?> addNewMedicine(AddMedicineRequest request){
        log.info("AddNewMedicine Start. Request: " + request);
        fieldValidator.validateMandatoryFields(request);
        //check if it is an approved medicine by fda.
        FdaApprovedMedicine approvedMedicine = apiFdaService.searchForMedicineByName(request.getName());
        if(approvedMedicine == null){
            log.error("Medicine is not approved by FDA. Not found in the FDA dataset.");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Medicine is not approved by FDA.");
        }
        log.info("FdaApprovedMedicine found!");
        if(request.getExpirationDate().isBefore(LocalDate.now())){
            log.error("Medicine is expired. Cannot continue.");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Medicine is expired.");
        }
        if(request.getStock() < 1){
            log.error("Stock is lower than 1. Cannot continue.");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Stock must be at least 1.");
        }
        //check the request category id and supplier id. because we cannot introduce a medicine from a supplier we dont work with.
        //also we support only the 3 types: antibiotics, supplements and medicine.
        if(!supplierRepository.existsById(request.getSupplierId())){
            log.error("Supplier id doesn't exist.");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Supplier id doesn't exist.");
        }
        if(!categoryRepository.existsById(request.getCategoryId())){
            log.error("Category id doesn't exist.");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Category id doesn't exist.");
        }
        //
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        Supplier supplier = supplierRepository.findById(request.getSupplierId()).get();
        Openfda medicineFdaAdditionalInfo = approvedMedicine.getResults().get(0).getOpenfda();
        String substanceName = medicineFdaAdditionalInfo.getSubstanceName().get(0);
        String producer = medicineFdaAdditionalInfo.getManufacturerName().get(0);

        Medicine medicine = new Medicine(request);
        medicine.setCategory(category);
        medicine.setSupplier(supplier);
        medicine.setSubstanceName(substanceName);
        medicine.setProducer(producer);
        log.info("Medicine to insert: " + medicine);
        medicineRepository.save(medicine);
        log.info("Medicine saved successfully.");
        Response response = new Response();
        response.setStatus(Response.Status.SUCCESS);
        response.setMessage("Saved success.");
        return response;


    }

    //get methods

    public Response<List<Medicine>> getMedicinesByCategoryId(Long categoryId){
        log.info("GetMedicinesByCategoryId start.");
        if(categoryId == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "CategoryId is null.");
        }
        log.info("CategoryId to search for: " + categoryId);
        List<Medicine> medicines = medicineRepository.findAllByCategory_Id(categoryId);
        log.info("List of medicines found: " + medicines);
        if(medicines.isEmpty()){
            Response<List<Medicine>> response = new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("No medicines found.");
            log.info("No medicines found.");
            return response;
        }
        Response<List<Medicine>> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        response.setData(medicines);
        return response;
    }

    public Response<List<Medicine>> getMedicinesBySubstanceName(String substanceName){
        log.info("GetMedicinesBySubstanceName start.");
        if(substanceName == null){
            throw new ApiException(HttpStatus.BAD_REQUEST, "SubstanceName is null.");
        }
        log.info("Substance name to search for: " + substanceName);
        List<Medicine> medicines = medicineRepository.findAllBySubstanceName(substanceName);
        if(medicines.isEmpty()){
            Response<List<Medicine>> response = new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("No medicines found.");
            return response;
        }
        Response<List<Medicine>> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        response.setData(medicines);
        return response;

    }

    public Response<?> getMedicines(Optional<String> medicineName){
        if(medicineName.isPresent() && !medicineName.get().trim().isEmpty()){
            return getMedicineByName(medicineName.get());
        }
        return getAllMedicines();
    }
    private Response<List<Medicine>> getAllMedicines(){
        log.info("Get all medicines start.");
        List<Medicine> medicines = medicineRepository.findAll();
        log.info("List of medicines found: " + medicines);
        if(medicines.isEmpty()){
            Response<List<Medicine>> response = new Response<>();
            response.setStatus(Response.Status.NOT_FOUND);
            response.setMessage("No medicines found.");
            log.info("No medicines found.");
            return response;
        }
        Response<List<Medicine>> response = new Response<>();
        response.setStatus(Response.Status.SUCCESS);
        response.setData(medicines);
        return response;
    }
    private Response<Medicine> getMedicineByName(String medicineName){
        log.info("MedicineName is present. Medicine to search for: " + medicineName);
        try{
            Optional<Medicine> medicine = medicineRepository.findByName(medicineName);
            if(medicine.isPresent()){
                log.info("Medicine found in table. Medicine: " + medicine.get());
                Response<Medicine> response =new Response<>();
                response.setStatus(Response.Status.SUCCESS);
                response.setData(medicine.get());
                return response;
            }else{
                String errMessage = String.format("Medicine %s was not found.",medicineName);
                log.info(errMessage);
                Response<Medicine> response =new Response<>();
                response.setStatus(Response.Status.NOT_FOUND);
                response.setMessage(errMessage);
                return response;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("Could not get entity from database.");
            Response<Medicine> response = new Response<>();
            response.setStatus(Response.Status.ERROR);
            response.setMessage("Could not get entity from database.");
            return response;
        }
    }
}
