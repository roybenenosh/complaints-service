package com.intuit.complaints.core;

import com.intuit.complaints.dal.Purchase;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PurchaseService {
    private final Map<UUID, Purchase> purchases;

    public PurchaseService() throws ParseException {
        purchases = Arrays.stream(new Purchase[]{
                new Purchase(UUID.fromString("f256c996-6dcb-40cf-8dce-a11fa9bcab6b"), UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"),UUID.fromString("4ac9da0b-66eb-415c-9153-a59ec0b3c3fe"), "Book", 13.2, "USD", 0.1, UUID.fromString("71e234d2-dc65-41ff-bada-9d08d82fc6d1"), getPurchaseDate("2020-11-21")),
                new Purchase(UUID.fromString("21d5dbe2-8369-459d-a955-a6b4f19b4d53"), UUID.fromString("a872d86a-c7cb-48b7-b5d9-f218d6845405"),UUID.fromString("8561769f-ff37-4ad4-b4f4-6ba3591cb279"), "Headphones", 129.99, "USD", 0, UUID.fromString("1a16b999-fe36-46a5-9604-ade9f4aa613d"), getPurchaseDate("2021-11-21")),
                new Purchase(UUID.fromString("340d04f5-4241-4cc3-bfb4-861c5c552891"), UUID.fromString("dcb6a039-b0fc-49dd-b5de-58856f66727d"),UUID.fromString("614c8f90-6108-461a-af22-425dee248691"), "TV", 1500, "USD", 0, UUID.fromString("bb7544cb-c4a6-412b-ab5b-2e8dc9872e68"), getPurchaseDate("2021-11-12")),
                new Purchase(UUID.fromString("30de333b-d7da-4906-b382-1a8ff59556ff"), UUID.fromString("bbbb080d-cffa-46d0-aa22-786c35d1a35b"),UUID.fromString("676a104a-b6c2-4bc5-8c1b-d0108098cb5b"), "Smartphone", 1000, "USD", 0, UUID.fromString("e3b088e4-54ad-4831-bf2d-23e4caca1d13"), getPurchaseDate("2021-11-10")),
                new Purchase(UUID.fromString("4933d1ce-9ca7-4640-ba17-e442057c44f1"), UUID.fromString("f22dff3f-06cf-49fe-97ec-bf7afe9a7fdb"),UUID.fromString("114449b0-41e7-44ac-ac0c-0a0433be9801"), "Laptop", 3000, "USD", 0, UUID.fromString("232ee131-16ee-4185-9086-600fae08a880"), getPurchaseDate("2021-10-21")),
                new Purchase(UUID.fromString("e11cc042-80f4-4a0b-b580-d1eedf6b153c"), UUID.fromString("72dddc34-f058-4d31-b370-e88f772ea8e8"),UUID.fromString("dca77154-97f9-480b-a842-95db0b048160"), "Speakers", 56.7, "USD", 0, UUID.fromString("7a0d66c2-212c-435b-8c6a-d561d302dceb"), getPurchaseDate("2021-09-21")),
                new Purchase(UUID.fromString("57cd80e4-ed5b-4d7e-a39d-15d039cb2069"), UUID.fromString("a90f6dd7-b74b-4be6-9065-daa1a92ba7ab"),UUID.fromString("036c1a2a-ab5b-4a6c-99f9-286c5a71e252"), "Envelopes", 6.99, "USD", 0, UUID.fromString("f37704de-3e3d-4586-bd1d-c3ab37dde15c"), getPurchaseDate("2021-06-21")),
                new Purchase(UUID.fromString("0797d85e-ae2e-482a-ae38-26ab83a8947e"), UUID.fromString("a227c2bd-358a-4587-95f0-61fb63678952"),UUID.fromString("ab6dc52c-bee4-433a-8337-d6dffa8eb37f"), "Scam", 1000000, "USD", 0, UUID.fromString("81e573b2-8bc5-44bf-9c3e-d2ca33ddc1a1"), getPurchaseDate("2021-01-21")),
                new Purchase(UUID.fromString("c2437271-ec14-40fe-92cc-22284ab3a25f"), UUID.fromString("8145b0d6-feb2-4ff6-8546-c0a5eece6f82"),UUID.fromString("2e2efc79-66c2-498d-bcee-507e06306df8"), "Frame", 13.99, "USD", 0, UUID.fromString("7ef40d26-31b7-4e50-93ea-7ed14dca6811"), getPurchaseDate("2021-12-21")),
                new Purchase(UUID.fromString("4b8d0786-a2e8-45cf-8d89-faa390c098df"), UUID.fromString("dcb6a039-b0fc-49dd-b5de-58856f66727d"),UUID.fromString("db98f5c9-80cb-4db0-b739-3f8f42b3c781"), "Cables", 20, "USD", 0, UUID.fromString("631ebad0-3e19-4334-b79a-f77101e17023"), getPurchaseDate("2021-03-21")),
                new Purchase(UUID.fromString("c7f07884-2faf-4c6e-8a51-c6256812d73b"), UUID.fromString("a227c2bd-358a-4587-95f0-61fb63678952"),UUID.fromString("1049d426-088a-4291-ab0c-2a462ef5eecb"), "Keyboard", 79.99, "USD", 0, UUID.fromString("b9a3d27c-2246-415d-89a2-c4b6fb16153a"), getPurchaseDate("2021-04-21")),
                new Purchase(UUID.fromString("af0676a6-542f-44c6-ae9d-1c0da4c4f1b3"), UUID.fromString("a90f6dd7-b74b-4be6-9065-daa1a92ba7ab"),UUID.fromString("12f7182a-34a5-4d6c-93a1-4d283badfa1f"), "Piggy Bank", 15, "USD", 0, UUID.fromString("6be6c9b6-35c6-4bef-bd9b-39f8fa3f6399"), getPurchaseDate("2021-06-21")),
        }).collect(Collectors.toMap(Purchase::getId, p -> p));
    }

    private Date getPurchaseDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new Date(dateFormat.parse(date).getTime());
    }

    public Purchase getPurchase(UUID id) {
        return purchases.get(id);
    }

    public static void main(String[] args) {
        IntStream.range(0, 12).forEach(i -> System.out.println(UUID.randomUUID()));
    }
}
