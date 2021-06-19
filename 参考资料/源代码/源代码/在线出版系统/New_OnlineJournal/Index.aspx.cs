using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace New_OnlineJournal
{
    public partial class Test : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            AuthorDataDataContext dataContext = new AuthorDataDataContext();
            AuthorAritcleDataDataContext aadc = new AuthorAritcleDataDataContext();
            var a = from x in dataContext.Author select x;
            var category = (from x in aadc.Category select x.category1).Distinct();
            ClientScript.RegisterStartupScript(ClientScript.GetType(), "myscript", "<script>autoclick();</script>");
            if (!this.IsPostBack)
            {
                author_list.DataSource = a;
                author_list.DataTextField = "name";
                author_list.DataValueField = "authorid";                
                author_list.DataBind();
                this.author_list.Items.Insert(0, new ListItem("--请选择--", "-1"));
                String chosen_author = author_list.SelectedItem.Text;
                AuthorDataDataContext dc = new AuthorDataDataContext();
                var ca = from x in dc.Author where x.name == chosen_author select x;
                int id = 0;
                foreach (var c in ca)
                    id = c.authorid;                
                var article_lister = from t1 in aadc.Article join t2 in aadc.OJAuthor on t1.articleid equals t2.articleid where t2.authorid == id select t1;
                article_list.DataSource = article_lister;
                article_list.DataTextField = "title";
                article_list.DataValueField = "articleid";
                article_list.DataBind();
                this.article_list.Items.Insert(0, new ListItem("--请选择--", "-1"));
                UpdatePanel2.Update();

                if (article_list.Items.Count != 0 && article_list.SelectedValue!="-1")
                {
                    String nid = article_list.SelectedValue;
                    AuthorAritcleDataDataContext naadc = new AuthorAritcleDataDataContext();
                    var abstr = from x in naadc.Article where x.articleid == Convert.ToInt32(nid) select x;
                    if (abstr.Count() != 0)
                        foreach (var x in abstr)
                            TextBox1.Text = x.@abstract;
                    UpdatePanel3.Update();
                }
                else
                    TextBox1.Text = "";             
                
            }

            String script = @"$('.selectpicker').selectpicker('refresh');";
            ScriptManager.RegisterClientScriptBlock(UpdatePanel2, typeof(UpdatePanel), "UpdatePanel2", script, true);
            ScriptManager.RegisterClientScriptBlock(UpdatePanel2, typeof(UpdatePanel), "UpdatePanel3", script, true);
        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            const String address = "http://files-1253208676.costj.myqcloud.com/publish/";
            if (article_list.Items.Count == 0 || article_list.SelectedItem.Text == "")
                return;
            else
            {
                    String str1 = "<script>window.open('" + address + Convert.ToString(article_list.SelectedItem.Value) + ".docx" + "','_blank')</script>";
                    //String str2 = "<script>window.open('" + address + Convert.ToString(article_list.SelectedItem.Value) + ".doc" + "','_blank')</script>";
                    Response.Write(str1);
                    //Response.Write(str2);
            }
        }

        protected void article_list_SelectedIndexChanged(object sender, EventArgs e)
        {
            String id = article_list.SelectedValue;
            AuthorAritcleDataDataContext aadc = new AuthorAritcleDataDataContext();
            var abstr = from x in aadc.Article where x.articleid == Convert.ToInt32(id) select x;
            if(abstr.Count()!=0)
            foreach (var x in abstr)
                TextBox1.Text = x.@abstract;
            UpdatePanel3.Update();
        }

        protected void author_list_SelectedIndexChanged(object sender, EventArgs e)
        {
            String chosen_author = author_list.SelectedItem.Text;

            AuthorDataDataContext dc = new AuthorDataDataContext();
            var ca = from x in dc.Author where x.name == chosen_author select x;
            int id = 0;
            foreach (var c in ca)
                id = c.authorid;
            AuthorAritcleDataDataContext aadc = new AuthorAritcleDataDataContext();
            var article_lister = from t1 in aadc.Article join t2 in aadc.OJAuthor on t1.articleid equals t2.articleid where t2.authorid == id select t1;
            article_list.DataSource = article_lister;
            article_list.DataTextField = "title";
            article_list.DataValueField = "articleid";
            article_list.DataBind();            
            UpdatePanel2.Update();

            String nid = article_list.SelectedValue;
            AuthorAritcleDataDataContext naadc = new AuthorAritcleDataDataContext();
            if (article_list.Items.Count != 0 && article_list.SelectedValue!="-1")
            {
                try
                {
                    var abstr = from x in naadc.Article where x.articleid == Convert.ToInt32(nid) select x;
                    if (abstr.Count() != 0)
                        foreach (var x in abstr)
                            TextBox1.Text = x.@abstract;
                }
                catch (Exception ee)
                {
                    Console.WriteLine(ee.Message);
                }
            }
            else
            {
                TextBox1.Text = "";
            }
            UpdatePanel3.Update();
        }

        //protected void UpdatePanel2_DataBinding(object sender, EventArgs e)
        //{
        //    string s3 = "<LINK   href=\"css/style.css\"   type=\"text/css\"   rel=\"stylesheet\" media=\"all\">";
        //    ScriptManager.RegisterStartupScript(article_list, Page.GetType(), "", s3, false);
        //    string script = "$('#UpdatePanel2').selectpicker('refresh');$('#article_list').selectpicker('render'); ";
        //    ScriptManager.RegisterStartupScript(this.Page, typeof(string), "scriptforusercontrol", script, true);
        //}
    }
}