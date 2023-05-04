//package JOOQ.service;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.CustomerDao;
//
//import lombok.RequiredArgsConstructor;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.daos.ParcelDao;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Parcel;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.MessageFormat;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@RequiredArgsConstructor
//@Transactional
//@Service(value = "ParcelServiceDAO")
//public class ParcelService {
//
//    private final ParcelDao ParcelDao;
//
//    public Parcel create(Parcel Parcel) {
//        ParcelDao.insert(Parcel);
//
//        return Parcel;
//    }
//
//    public Parcel update(Parcel Parcel) {
//        ParcelDao.update(Parcel);
//        return Parcel;
//    }
//
//    public List<Parcel> getAll() {
//        return ParcelDao.findAll();
//    }
//
//
//    public Parcel getOne(long id) {
//        Parcel Parcel = ParcelDao.findById(id);
//        if(Parcel == null){
//            throw new NoSuchElementException(MessageFormat.format("Parcel id {0} not found", String.valueOf(id)));
//        }
//        return Parcel;
//    }
//
//    public void deleteById(long id) {
//        ParcelDao.deleteById(id);
//    }
//}
//
//
//
