package electronicStore.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class pagebleResponse <T> {

    public List<T> content;
    public int pageNumber;
    public Integer pageSize;
    public Long totalElements;
    public Integer totalPages;
    public Boolean lastPage;
}
