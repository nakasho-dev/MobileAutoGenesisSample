// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "SampleApp",
    platforms: [.iOS(.v16)],
    products: [.library(name: "SampleAppCore", targets: ["SampleAppCore"])],
    targets: [.target(name: "SampleAppCore", path: "SampleApp")]
)
