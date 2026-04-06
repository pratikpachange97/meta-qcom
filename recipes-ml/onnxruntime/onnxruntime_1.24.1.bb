SUMMARY = "ONNX Runtime"
DESCRIPTION = "ONNX Runtime is a cross-platform, high-performance inference \
engine for machine learning models in the Open Neural Network Exchange (ONNX) \
format. Developed by Microsoft, it provides optimized execution of ONNX models \
across a variety of hardware targets including CPUs, GPUs, and dedicated AI accelerators."
HOMEPAGE = "https://github.com/microsoft/onnxruntime"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f7e3b1308cb5c00b372a6e78835732d"

OECMAKE_SOURCEPATH = "${S}/cmake"

inherit cmake

# onnxruntime 1.24.1 uses CMake FetchContent for all of the dependencies
# listed below.  None of them are git submodules, so every one must be
# declared in SRC_URI and its location forwarded to CMake via
# FETCHCONTENT_SOURCE_DIR_<UPPERCASENAME> so that
# FETCHCONTENT_FULLY_DISCONNECTED=ON does not abort the configure step.
#
# All GitHub source archives are fetched via git (not zip archives) to
# satisfy the [src-uri-bad] QA check.  destsuffix= places each checkout in
# a named subdirectory of WORKDIR so FETCHCONTENT_SOURCE_DIR_* can point at
# it.
#
# For entries marked AUTOREV, replace with the commit the tag resolves to:
#   git ls-remote <repo-url> refs/tags/<tag>
# pytorch_cpuinfo and eigen3 already carry the commit hash in the upstream URL.

SRC_URI = "gitsm://github.com/microsoft/onnxruntime.git;protocol=https;branch=rel-${PV};name=ort \
    git://github.com/abseil/abseil-cpp.git;protocol=https;nobranch=1;name=abseil;destsuffix=abseil-cpp \
    git://github.com/google/re2.git;protocol=https;nobranch=1;name=re2;destsuffix=re2 \
    git://github.com/protocolbuffers/protobuf.git;protocol=https;nobranch=1;name=protobuf;tag=v21.12;destsuffix=protobuf \
    git://github.com/HowardHinnant/date.git;protocol=https;nobranch=1;name=date;tag=v3.0.1;destsuffix=date \
    git://github.com/boostorg/mp11.git;protocol=https;nobranch=1;name=mp11;tag=boost-1.82.0;destsuffix=mp11 \
    git://github.com/nlohmann/json.git;protocol=https;nobranch=1;name=nlohmann_json;tag=v3.11.3;destsuffix=nlohmann_json \
    git://github.com/pytorch/cpuinfo.git;protocol=https;nobranch=1;name=pytorch_cpuinfo;destsuffix=pytorch_cpuinfo \
    git://github.com/microsoft/GSL.git;protocol=https;nobranch=1;name=gsl;tag=v4.0.0;destsuffix=gsl \
    git://github.com/dcleblanc/SafeInt.git;protocol=https;nobranch=1;name=safeint;tag=3.0.28;destsuffix=safeint \
    git://github.com/google/flatbuffers.git;protocol=https;nobranch=1;name=flatbuffers;tag=v23.5.26;destsuffix=flatbuffers \
    git://github.com/onnx/onnx.git;protocol=https;nobranch=1;name=onnx;tag=v1.20.1;destsuffix=onnx \
    git://github.com/eigen-mirror/eigen.git;protocol=https;nobranch=1;name=eigen3;destsuffix=eigen3 \
    https://github.com/protocolbuffers/protobuf/releases/download/v21.12/protoc-21.12-linux-x86_64.zip;name=protoc;subdir=protoc_binary \
"

SRCREV_FORMAT = "ort_abseil_re2_protobuf_date_mp11_nlohmann_json_pytorch_cpuinfo_gsl_safeint_flatbuffers_onnx_eigen3"
SRCREV_ort             = "470ae16099a74fe05e31f2530489332c0525edb5"
SRCREV_abseil          = "987c57f325f7fa8472fa84e1f885f7534d391b0d"
SRCREV_re2             = "cf8f19116192016936b306b033f9860cff6f0b5c"
SRCREV_protobuf        = "f502b8e9c831bda0bea57d9cbeefca3eb76e4254"
SRCREV_date            = "6e921e1b1d21e84a5c82416ba7ecd98e33a436d0"
SRCREV_mp11            = "0a0b5fb001ce0233ae3a6f99d849c0649e5a7361"
SRCREV_nlohmann_json   = "9cca280a4d0ccf0c08f47a99aa71d1b0e52f8d03"
SRCREV_pytorch_cpuinfo = "403d652dca4c1046e8145950b1c0997a9f748b57"
SRCREV_gsl             = "1fcf53a2f64c72c76f5d84adb50d41e6c4467d23"
SRCREV_safeint         = "4cafc9196c4da9c817992b20f5253ef967685bf8"
SRCREV_flatbuffers     = "c20d64b8de759423af61e072fcabf916c1f7bf9f"
SRCREV_onnx            = "d3f6b795aedb48eaecc881bf5e8f5dd6efbe25b3"
SRCREV_eigen3          = "1d8b82b0740839c0de7f1242a3585e3390ff5f33"
SRC_URI[protoc.sha256sum] = "3a4c1e5f2516c639d3079b1586e703fc7bcfa2136d58bda24d1d54f949c315e8"

DEPENDS += " \
    cmake-native \
    zlib \
"

EXTRA_OECMAKE=" \
    -DCMAKE_BUILD_TYPE=Release \
    -DCMAKE_FIND_ROOT_PATH=${STAGING_DIR_TARGET} \
    -DCMAKE_SYSTEM_PROCESSOR=arm64 \
    -Dprotobuf_WITH_ZLIB=OFF \
    -Donnxruntime_BUILD_SHARED_LIB=ON \
    -Donnxruntime_DEV_MODE=OFF \
    -DFETCHCONTENT_FULLY_DISCONNECTED=ON \
    -DFETCHCONTENT_SOURCE_DIR_ABSEIL_CPP=${WORKDIR}/sources/abseil-cpp \
    -DFETCHCONTENT_SOURCE_DIR_RE2=${WORKDIR}/sources/re2 \
    -DFETCHCONTENT_SOURCE_DIR_PROTOBUF=${WORKDIR}/sources/protobuf \
    -DFETCHCONTENT_SOURCE_DIR_DATE=${WORKDIR}/sources/date \
    -DFETCHCONTENT_SOURCE_DIR_MP11=${WORKDIR}/sources/mp11 \
    -DFETCHCONTENT_SOURCE_DIR_NLOHMANN_JSON=${WORKDIR}/sources/nlohmann_json \
    -DFETCHCONTENT_SOURCE_DIR_PYTORCH_CPUINFO=${WORKDIR}/sources/pytorch_cpuinfo \
    -DFETCHCONTENT_SOURCE_DIR_GSL=${WORKDIR}/sources/gsl \
    -DFETCHCONTENT_SOURCE_DIR_SAFEINT=${WORKDIR}/sources/safeint \
    -DFETCHCONTENT_SOURCE_DIR_FLATBUFFERS=${WORKDIR}/sources/flatbuffers \
    -DFETCHCONTENT_SOURCE_DIR_ONNX=${WORKDIR}/sources/onnx \
    -DFETCHCONTENT_SOURCE_DIR_EIGEN3=${WORKDIR}/sources/eigen3 \
    -DFETCHCONTENT_SOURCE_DIR_PROTOC_BINARY=${WORKDIR}/sources/protoc_binary \
    -DONNX_CUSTOM_PROTOC_EXECUTABLE=${WORKDIR}/sources/protoc_binary/bin/protoc \
    -Donnxruntime_BUILD_UNIT_TESTS=OFF \
    -Donnxruntime_RUN_ONNX_TESTS=OFF \
"

FILES:${PN} = " \
    ${libdir}/libonnxruntime.so.* \
    ${libdir}/libonnxruntime_providers_*.so \
"

FILES:${PN}-dev = " \
    ${includedir}/* \
    ${libdir}/cmake/* \
    ${libdir}/pkgconfig/* \
    ${libdir}/libonnxruntime.so \
"