package io.spinme;


import java.util.Date;

public class InvoiceDto {

    public int Id;

    /// <summary>
    /// OrderStatus due to the OrderStatus enum.
    /// New, Placed, Completed, Rejected
    /// </summary>
    public int OrderStatus;

    public long AmountNet;
    public long AmountGross;
    public long AmountWithDiscount;
    public float VatRate;


    public String Description;

    public Date InvoiceCreatedAt = new Date();

    public Date InvoiceUpdatedAt = new Date();

    public Date PaymentDate = new Date();

    public String UserFirstName;

    public String UserLastName;

    public String UserEmail;

    public String CompanyName;

    public String TaxNumber;

    public String Country;

    public String Address;

    public String HouseNumber;

    public String FlatNumber;

    public String ZipCode;

    public String City;

    public int DiscountPercentage;

    public String Title;


    public InvoiceDto(){}

    public InvoiceDto(int id, String title, int orderStatus, long amountNet, long amountGross, float vatRate, int discountPercentage, long amountWithDiscount, String description, Date invoiceCreatedAt, Date invoiceUpdatedAt, Date paymentDate, String userFirstName, String userLastName, String userEmail, String companyName, String taxNumber, String country, String address, String houseNumber, String flatNumber, String zipCode, String city) {
        Id = id;
        OrderStatus = orderStatus;
        AmountNet = amountNet;
        AmountGross = amountGross;
        AmountWithDiscount = amountWithDiscount;
        VatRate = vatRate;
        Description = description;
        InvoiceCreatedAt = invoiceCreatedAt;
        InvoiceUpdatedAt = invoiceUpdatedAt;
        PaymentDate = paymentDate;
        UserFirstName = userFirstName;
        UserLastName = userLastName;
        UserEmail = userEmail;
        CompanyName = companyName;
        TaxNumber = taxNumber;
        Country = country;
        Address = address;
        HouseNumber = houseNumber;
        FlatNumber = flatNumber;
        ZipCode = zipCode;
        City = city;
        DiscountPercentage = discountPercentage;
        Title = title;

    }
}

