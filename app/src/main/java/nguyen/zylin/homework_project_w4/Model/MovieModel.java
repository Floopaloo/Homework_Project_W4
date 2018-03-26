package nguyen.zylin.homework_project_w4.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nmdli on 14-Mar-18.
 */

public class MovieModel {

        @SerializedName("results")
        @Expose
        private List<ResultModel> results = null;
        @SerializedName("page")
        @Expose
        private Integer page;
        @SerializedName("total_results")
        @Expose
        private Integer totalResults;
        @SerializedName("dates")
        @Expose
        private DatesModel dates;
        @SerializedName("total_pages")
        @Expose
        private Integer totalPages;

        public List<ResultModel> getResults() {
            return results;
        }

        public void setResults(List<ResultModel> results) {
            this.results = results;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }

        public DatesModel getDates() {
            return dates;
        }

        public void setDates(DatesModel dates) {
            this.dates = dates;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

}
