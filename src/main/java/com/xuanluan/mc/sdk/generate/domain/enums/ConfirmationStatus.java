package com.xuanluan.mc.sdk.generate.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConfirmationStatus {
    approving(1, "Chờ phê duyệt"),
    processing(2, "Đang hoạt động"),
    closing(3, "Tạm ngưng");

    private final int value;
    private final String label;
}
