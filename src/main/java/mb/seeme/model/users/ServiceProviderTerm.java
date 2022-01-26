package mb.seeme.model.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderTerm{

    private Long providerId;
    private String providerName;
    private String providerField;
    private String providerDescription;
    private String address;
    private String city;
    private LocalDate termDate;
    private LocalTime termTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceProviderTerm that = (ServiceProviderTerm) o;
        return Objects.equals(providerName, that.providerName) && Objects.equals(providerField, that.providerField) && Objects.equals(providerDescription, that.providerDescription) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerName, providerField, providerDescription, address);
    }
}
