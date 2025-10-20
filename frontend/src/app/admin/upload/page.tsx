import FileUpload from "@/components/FileUpload";

const Upload = () => {
  return (
    <div className="flex flex-col  p-6 space-y-6">
      <h2 className="text-2xl font-semibold text-gray-800">
        Importer des fichiers de note
      </h2>
      <FileUpload />
    </div>
  );
};
export default Upload;
