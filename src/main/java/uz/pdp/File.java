package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

    String name;
    String originalFilename;
    String uuid;
    String contentType;

}
