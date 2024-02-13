package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.AddParkingLotRequest;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

    private final String FOLDER_PATH="C:\\Users\\Abine\\OneDrive\\Desktop\\MyFIles";
    public ParkingLot addParkingLot(AddParkingLotRequest dto, MultipartFile file) throws IOException {
        ParkingLot parkingLot = MappingUtil.mapAddParkingLotToParkingLot(dto);
        String filename = generateUniqueFilename() + "_" + file.getOriginalFilename();
        String filePath = FOLDER_PATH + filename;

        try {
            Path destinationPath = Path.of(filePath);
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Handle the exception appropriately
            throw new IOException("Failed to save the file: " + filename, e.getCause());
        }

        parkingLot.setRating(5.0f);
        parkingLot.setAvailableSlot(parkingLot.getCapacity());
        parkingLot.setImageUrl(filename);
        return parkingLotRepository.save(parkingLot);
    }


    private String generateUniqueFilename() {
        // Generate a timestamp string
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);

        // Generate a random string
        String randomString = UUID.randomUUID().toString().replaceAll("-", "");

        // Combine the timestamp and random string to create a unique filename
        String uniqueFilename = timestamp + "_" + randomString;

        return uniqueFilename;
    }

}
