package com.example.temp.machine.application;

import com.example.temp.common.exception.ApiException;
import com.example.temp.common.exception.ErrorCode;
import com.example.temp.machine.domain.BodyPart.BodyCategory;
import com.example.temp.machine.domain.Machine;
import com.example.temp.machine.domain.MachineRepository;
import com.example.temp.machine.dto.request.MachineBulkCreateRequest;
import com.example.temp.machine.dto.request.MachineCreateRequest;
import com.example.temp.machine.dto.request.MachineSearchUsingBodyPartRequest;
import com.example.temp.machine.dto.response.MachineCreateResponse;
import com.example.temp.machine.dto.response.MachineInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MachineService {

    public static final int MAX_BODY_SIZE_PER_MACHINE = 1;

    private final MachineRepository machineRepository;

    @Transactional
    public MachineCreateResponse createMachine(MachineCreateRequest request) {
        if (machineRepository.existsByName(request.name())) {
            throw new ApiException(ErrorCode.MACHINE_ALREADY_REGISTER);
        }
        if (request.bodyParts().size() > MAX_BODY_SIZE_PER_MACHINE) {
            throw new ApiException(ErrorCode.MACHINE_MATCH_ONLY_ONE_BODY_PART);
        }
        Machine machine = Machine.create(request.name(), request.bodyParts());
        Machine savedMachine = machineRepository.save(machine);
        return MachineCreateResponse.from(savedMachine);
    }

    @Transactional
    public List<MachineCreateResponse> createMachinesBulk(MachineBulkCreateRequest request) {
        return request.machines().stream()
            .map(this::createMachine)
            .toList();
    }

    public List<MachineInfo> searchUsingBodyPart(MachineSearchUsingBodyPartRequest request) {
        return machineRepository.findAllByBodyPart(request.bodyPart()).stream()
            .map(MachineInfo::from)
            .toList();
    }

    public void searchUsingBodyCategory(BodyCategory category) {

    }
}
